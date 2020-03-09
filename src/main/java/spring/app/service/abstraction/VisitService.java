package spring.app.service.abstraction;

import spring.app.dto.VisitDto;

public interface VisitService {

    void registerTelegramUserAndVisit(VisitDto visitDto);
}
