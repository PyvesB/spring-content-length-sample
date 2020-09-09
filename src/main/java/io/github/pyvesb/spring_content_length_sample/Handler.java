package io.github.pyvesb.spring_content_length_sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class Handler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        Flux<DataBuffer> inputBuffers = serverRequest.body(BodyExtractors.toDataBuffers())
                .cache()
                .map(d -> d.slice(0, d.readableByteCount()));

        // Fork off processing using inputBuffers.
        Disposable parallelSubscription = inputBuffers
                .doOnNext(dataBuffer -> LOGGER.info("Processing data buffer in parallel: {}", dataBuffer))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();

        return inputBuffers
                .doOnNext(dataBuffer -> LOGGER.info("Received input data buffer: {}", dataBuffer))
                .doOnCancel(() -> parallelSubscription.dispose())
                .doOnCancel(() -> LOGGER.info("Pipeline cancelled"))
                .then(ServerResponse.accepted().build());
    }
}
