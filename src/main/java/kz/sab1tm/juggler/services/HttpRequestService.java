package kz.sab1tm.juggler.services;

import javafx.scene.paint.Color;
import kz.sab1tm.juggler.models.HttpRequestHeaders;
import kz.sab1tm.juggler.models.HttpRequestParams;
import kz.sab1tm.juggler.models.HttpResponse;
import kz.sab1tm.juggler.models.enums.HttpMethodEnum;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class HttpRequestService {

    public HttpResponse sendRequest(HttpMethodEnum method,
                                    String url,
                                    HttpRequestParams params,
                                    HttpRequestHeaders headers,
                                    String body) {
        Request request = buildRequest(method, url, params, headers, body);
        return executeRequest(request);
    }

    public Request buildRequest(HttpMethodEnum method,
                                String url,
                                HttpRequestParams params,
                                HttpRequestHeaders headers,
                                String body) {

        HttpUrl finalUrl = buildUrlWithParams(url, method, params);
        Request.Builder requestBuilder = new Request.Builder().url(finalUrl);
        addHeaders(requestBuilder, headers);
        RequestBody requestBody = createRequestBody(method, params, headers, body);
        setMethodAndBody(requestBuilder, method, requestBody);
        return requestBuilder.build();
    }

    private HttpResponse executeRequest(Request request) {
        HttpResponse response = new HttpResponse();
        OkHttpClient client = new OkHttpClient();
        long startTime = System.currentTimeMillis();

        try (Response okHttpResponse = client.newCall(request).execute()) {
            response.setCode(okHttpResponse.code());
            response.setStatus(okHttpResponse.code() + " " + okHttpResponse.message());
            if (okHttpResponse.isSuccessful()) {
                response.setStatusColor(Color.GREEN);
            } else {
                response.setStatusColor(Color.RED);
            }
            if (okHttpResponse.body() != null) {
                response.setBody(okHttpResponse.body().string());
                response.setSize(okHttpResponse.body().contentLength());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        response.setDuration(System.currentTimeMillis() - startTime);
        return response;
    }

    private HttpUrl buildUrlWithParams(String url, HttpMethodEnum method, HttpRequestParams params) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (method == HttpMethodEnum.GET && hasParams(params)) {
            for (Map.Entry<String, String> param : params.getParams().entrySet()) {
                urlBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }
        return urlBuilder.build();
    }

    private void addHeaders(Request.Builder requestBuilder, HttpRequestHeaders headers) {
        if (hasHeaders(headers)) {
            for (Map.Entry<String, String> header : headers.getHeaders().entrySet()) {
                requestBuilder.addHeader(header.getKey(), header.getValue());
            }
        }
    }

    private RequestBody createRequestBody(HttpMethodEnum method, HttpRequestParams params, HttpRequestHeaders headers, String body) {
        if (body != null) {
            String contentType = getContentType(headers);
            MediaType mediaType = MediaType.parse(contentType);
            return RequestBody.create(body, mediaType);
        } else if (method.allowsRequestBody() && hasParams(params)) {
            return createFormBody(params);
        }
        return null;
    }

    private String getContentType(HttpRequestHeaders headers) {
        if (headers != null && headers.getHeaders().containsKey("Content-Type")) {
            return headers.getHeaders().get("Content-Type");
        }
        return "application/json; charset=utf-8";
    }

    private RequestBody createFormBody(HttpRequestParams params) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> param : params.getParams().entrySet()) {
            formBuilder.add(param.getKey(), param.getValue());
        }
        return formBuilder.build();
    }

    private void setMethodAndBody(Request.Builder requestBuilder, HttpMethodEnum method, RequestBody requestBody) {
        switch (method) {
            case GET:
                requestBuilder.get();
                break;
            case POST:
                requestBuilder.post(getRequestBodyOrEmpty(requestBody));
                break;
            case PUT:
                requestBuilder.put(getRequestBodyOrEmpty(requestBody));
                break;
            case DELETE:
                if (requestBody != null) {
                    requestBuilder.delete(requestBody);
                } else {
                    requestBuilder.delete();
                }
                break;
            case PATCH:
                requestBuilder.patch(getRequestBodyOrEmpty(requestBody));
                break;
            case HEAD:
                requestBuilder.head();
                break;
            case OPTIONS:
                requestBuilder.method(method.name(), requestBody);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
    }

    private RequestBody getRequestBodyOrEmpty(RequestBody requestBody) {
        return requestBody != null ? requestBody : RequestBody.create(new byte[0], null);
    }

    private boolean hasParams(HttpRequestParams params) {
        return params != null && params.getParams() != null && !params.getParams().isEmpty();
    }

    private boolean hasHeaders(HttpRequestHeaders headers) {
        return headers != null && headers.getHeaders() != null && !headers.getHeaders().isEmpty();
    }
}
