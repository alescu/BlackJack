package cat.itacademy.s05.t01.n01.repository;

import cat.itacademy.s05.t01.n01.model.Card;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CardRepository extends ReactiveCrudRepository<Card, Integer> {
}
