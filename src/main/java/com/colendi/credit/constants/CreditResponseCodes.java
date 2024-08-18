package com.colendi.credit.constants;

import lombok.Getter;

@Getter
public enum CreditResponseCodes {
    E_INVALID_INSTALLMENT_ID("Lütfen geçerli bir kredi numarası giriniz."),
    E_INVALID_USER_ID("Lütfen geçerli bir kullanıcı ile işlem yapınız."),
    E_INVALID_CREDIT_ID("Girdiğiniz kredi numarasına ait işlem bulunmamaktadır."),
    E_INVALID_CURRENCY("Para birimi geçerli değil."),
    E_INVALID_AMOUNT("Lütfen geçerli bir tutar giriniz."),
    E_INVALID_INSTALLMENT_COUNT("Lütfen geçerli bir taskit sayısı giriniz"),
    E_INVALID_CREDIT_TYPE("Talep ettiğiniz kredi türü sistem tarafından desteklenmemektedir."),
    E_CREDIT_NOT_FOUND("Girdiğiniz bilgilerle uyuşan bir kredi bulunmamaktadır."),
    E_INSTALLMENT_NOT_FOUND("Taksit bulunamadı"),
    SYSTEM_ERROR("İşleminizi şimdi gerçekleştiremiyoruz, lütfen daha sonra tekrar deneyiniz."),
    E_USER_NOT_FOUND("Müşteri numarası geçersiz."),
    E_INTEREST_CALCULATION_IN_PROGRESS("Sistemimizde faiz hesaplama işlemi devam ettiği için işleminizi gerçekleştiremiyoruz, Lütfen daha sonra tekrar deneyiniz"),
    E_KEY_NOT_FOUND("Faiz oranı bulunamadı"),
    SUCCESS("Success");


    private final String message;

    CreditResponseCodes(String message) {
        this.message = message;
    }
}
