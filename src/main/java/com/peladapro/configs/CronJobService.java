package com.peladapro.configs;

import com.peladapro.service.PlayerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CronJobService {

    private final PlayerService playerService;

    public CronJobService(PlayerService playerService) {
        this.playerService = playerService;
    }

    // Todo domingo às 05:00
    @Scheduled(cron = "0 0 20 * * 6", zone = "America/Sao_Paulo")
    public void tarefaCincoHoras() {
        System.out.println(LocalDateTime.now() + "Executando sabado às 20:00");
        playerService.setCanVote(true);
    }

    // Todo domingo às 07:20
    @Scheduled(cron = "0 50 7 * * 0", zone = "America/Sao_Paulo") // 0 = domingo
    public void tarefaSeteVinte() {
        System.out.println(LocalDateTime.now() + "Executando domingo às 07:20");
        playerService.setCanVote(false);
    }
}
