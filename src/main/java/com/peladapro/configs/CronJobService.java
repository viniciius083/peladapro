package com.peladapro.configs;

import com.peladapro.service.PlayerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronJobService {

    private final PlayerService playerService;

    public CronJobService(PlayerService playerService) {
        this.playerService = playerService;
    }

    // Todo domingo às 05:00
    @Scheduled(cron = "0 0 5 * * 0") // 0 = domingo
    public void tarefaCincoHoras() {
        System.out.println("Executando domingo às 05:00");
        playerService.setCanVote(true);
    }

    // Todo domingo às 07:20
    @Scheduled(cron = "0 20 7 * * 0") // 0 = domingo
    public void tarefaSeteVinte() {
        System.out.println("Executando domingo às 07:20");
        playerService.setCanVote(false);
    }
}
