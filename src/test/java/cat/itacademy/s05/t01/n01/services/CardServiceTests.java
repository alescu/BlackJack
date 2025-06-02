package cat.itacademy.s05.t01.n01.services;

import cat.itacademy.s05.t01.n01.model.Card;
import cat.itacademy.s05.t01.n01.repository.CardRepository;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


@RunWith(SpringRunner.class)
@DataR2dbcTest
@Import(CardService.class)
public class CardServiceTests {

    @Autowired
    private CardService cardService;

    @Test
    public void getCardsListMono() {
        Flux<Card> cardsFlux = cardService.getCardsListMono();
        StepVerifier.create(cardsFlux)
                .expectNextCount(52)
                .verifyComplete();
    }

    @Test
    public void getShuffledCardsListMono() {
        Mono<List<Card>> cardsFlux_1 = cardService.getShuffledCardsListMono();
        Mono<List<Card>> cardsFlux_2 = cardService.getShuffledCardsListMono();
        Mono<Object[]> combinedShuffles = Mono.zip(cardsFlux_1, cardsFlux_2).map(t -> new Object[]{t.getT1(), t.getT2()});
        StepVerifier.create(combinedShuffles).expectNextMatches( result -> {
            @SuppressWarnings("unchecked")
            List<Card> list1 = (List<Card>) result[0];
            @SuppressWarnings("unchecked")
            List<Card> list2 = (List<Card>) result[1];
            return !list1.getFirst().equals(list2.getFirst());
        }).verifyComplete();
    }

}
