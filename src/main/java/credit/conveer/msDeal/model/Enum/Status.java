package credit.conveer.msDeal.model.Enum;

/**
 * статус
 */
public enum Status {

    PREAPPROVAL,// (Предодобрена)
    APPROVED,// (Одобрена)
    CC_DENIED,// (Отклонена Кредитным Конвейером)
    CC_APPROVED,// (Одобрена Кредитным Конвейером)
    PREPARE_DOCUMENTS,// (Подготовка документов)
    DOCUMENT_CREATED,// (Документы созданы)
    CLIENT_DENIED,// (Отклонено Клиентом)
    DOCUMENT_SIGNED,// (Документы подписаны)
    CREDIT_ISSUED;// (Кредит выдан)
}
