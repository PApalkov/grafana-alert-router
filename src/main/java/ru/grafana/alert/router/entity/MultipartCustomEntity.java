package ru.grafana.alert.router.entity;

import lombok.RequiredArgsConstructor;
import org.apache.http.ContentTooLongException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

import java.io.*;

@RequiredArgsConstructor
public class MultipartCustomEntity implements HttpEntity {

    private final HttpEntity multipartHttpEntity;

    @Override
    public boolean isRepeatable() {
        return true;
    }

    @Override
    public boolean isChunked() {
        return multipartHttpEntity.isChunked();
    }

    @Override
    public long getContentLength() {
        return multipartHttpEntity.getContentLength();
    }

    @Override
    public Header getContentType() {
        return multipartHttpEntity.getContentType();
    }

    @Override
    public Header getContentEncoding() {
        return multipartHttpEntity.getContentEncoding();
    }

    @Override
    public InputStream getContent() throws IOException, UnsupportedOperationException {
        if (this.getContentLength() < 0) {
            throw new ContentTooLongException("Content length is unknown");
        }

        final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        writeTo(outStream);
        outStream.flush();
        return new ByteArrayInputStream(outStream.toByteArray());
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        multipartHttpEntity.writeTo(outputStream);
    }

    @Override
    public boolean isStreaming() {
        return multipartHttpEntity.isStreaming();
    }

    @Override
    public void consumeContent() throws IOException {
        multipartHttpEntity.consumeContent();
    }
}
