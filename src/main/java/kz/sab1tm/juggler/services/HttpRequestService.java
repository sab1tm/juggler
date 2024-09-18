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
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static kz.sab1tm.juggler.utils.HttpStatusUtil.toPrettyStatus;

public class HttpRequestService {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestService.class);

    public HttpResponse sendRequest(HttpMethodEnum method,
                                    String url,
                                    HttpRequestParams params,
                                    HttpRequestHeaders headers,
                                    String body) {
        Request request = buildRequest(method, url, params, headers, body);
        return executeRequest(request);
    }

    private Request buildRequest(HttpMethodEnum method,
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
            long duration = System.currentTimeMillis() - startTime;
            response.setStatusColor(Color.RED);
            response.setDuration(String.valueOf(duration));
            response.setCode(okHttpResponse.code());
            response.setStatus(toPrettyStatus(okHttpResponse.code()));
            if (okHttpResponse.isSuccessful()) {
                response.setStatusColor(Color.GREEN);

                ResponseBody responseBody = okHttpResponse.body();
                if (responseBody != null) {
                    response.setContentType(responseBody.contentType());
                    String body = responseBody.string();
                    response.setBody(body);
                    response.setSize(String.valueOf(body.getBytes().length));
                }
            }
        } catch (IOException e) {
            response.setStatus(e.getClass().getSimpleName());
            response.setStatusColor(Color.RED);
            log.error("ERROR: {}", e.getMessage());
        }

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
