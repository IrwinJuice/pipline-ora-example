package org.example.piplintora;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@RestController
public class DemoController {

    public DemoController(DatabaseClient dc) {
        this.dc = dc;
    }


    private final DatabaseClient dc;

    @CrossOrigin(origins = "*")
    @GetMapping("/feta")
    public Flux<Feta> getFeta() {
        String sql = """
                SELECT *
                FROM   TABLE(get_tab_ptf(50000))
                ORDER BY id DESC
                """;

        return dc.sql(sql)
                .fetch()
                .all()
                .map(row -> new Feta(row.get("description").toString(), (BigDecimal) row.get("id")));
    }


}
