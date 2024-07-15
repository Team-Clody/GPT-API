package org.sopt.gptapi.domain.reply;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends ReactiveCrudRepository<Reply, Long> {

}
