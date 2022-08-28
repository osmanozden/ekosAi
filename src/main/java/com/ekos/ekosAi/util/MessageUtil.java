package com.ekos.ekosAi.util;

public enum MessageUtil {
  USER_CONNECTED_WITH_DEBIT("KULLANICI BİR ZİMMETE BAĞLI!","user_connected_with_debit"),
  // INVALID_CELLPHONE_ERROR("Telefon numarası formatı uygun değil!","invalid_cellphone_error"),
  DUPLICATE_USERNAME_ERROR("Kullanıcı adı daha önce alınmış!", "duplicate_username_error"),
  DUPLICATE_NAME_ERROR("Bu isim daha önce alınmış!", "duplicate_name_error"),
  DUPLICATE_EMAIL_ERROR("Bu maile kayıtlı bir hesap mevcut!", "duplicate_email_error"),
  PASSWORD_VALIDATION_ERROR("Şifre doğrulanamadı!", "password_validation_error"),
  SEND_EMAIL_ERROR("Mail gönderilirken bir hata oluştu!", "send_email_error"),
  USER_NOT_FOUND("Kullanıcı Bulunamadı", "user_not_found"),
  CONFIRMATION_NOT_CORRECT("Geçersiz Doğrulama Kodu Girildi", "confirmation_not_correct"),
  NOT_FOUND("İlgili Veri bulunamadı", "no_data_found"),
  CREATE_ATTRIBUTE_SUCCESS("Özellik Oluşturuldu", "create_attribute_success"),
  INVITATION_NOT_CORRECT("Geçersiz Davet", "invitation_not_correct"),
  EMAIL_ERROR("Email bulunumadı", "email_cannot_find"),
  PASSWORD_ERROR("Şifre alanı boş olamaz!", "password_cannot_be_empty"),
  RESET_KEY_NOT_CORRECT("Reset key doğrulanamadı!", "reset_key_not_correct"),
  THE_LINK_BROKEN("Bağlantı bozuk veya süresi dolmuş!", "the_link_broken"),
  INVALID_JWT_TOKEN("JWT Token doğrulanamadı", "invalid_jwt_token"),
  COMPANY_USED_BY_CONTACT(
      "Company bir veya daha çok contact tarafından kullanılıyor", "company_used_by_contact"),
  COMPANY_USED_BY_POSITION(
      "Company bir veya daha çok pozisyon tarafından kullanılıyor", "company_used_by_position"),
  COMPANY_USED_BY_DEBIT("Company bir veya daha çok zimmet kaydına sahip", "company_used_by_debit"),
  COMPANY_USED_BY_CONTACT_AND_POSITION(
      "Company bir veya daha çok position ve contact tarafından kullanılıyor",
      "company_used_by_contact_and_position"),
  POSITION_USED_BY_PROFILE(
      "Pozisyon bir veya daha çok profil tarafından kullanılıyor", "position_used_by_profile"),
  USER_ALREADY_IN_THIS_TEAM("Davet ettiğiniz kişi zaten üye", "user_already_in_this_team"),
  USER_ALREADY_IN_THIS_COMPANY(
      "Davet ettiğiniz kişi zaten bu firmada", "user_already_in_this_company"),
  USER_ALREADY_IN_ANOTHER_COMPANY(
      "Davet ettiğiniz kişi başka bir firmada çalışıyor ", "user_already_in_another_company"),
  ADMIN_NAME_NOT_ACCEPT("Bu isimde role oluşturamazsınız", "admin_name_not_accept"),
  DATE_CAN_NOT_BE_EMPTY("Tarih alanları boş bırakalımaz", "dates_can_not_be_empty"),
  CAN_NOT_FOUND_THE_EMPLOYEE("Böyle bir çalışan bulunamadı", "can_not_found_the_employee"),
  CAN_NOT_FOUND_THE_COMPANY("Firma bulunamadı", "can_not_found_the_company"),
  USER_CONNECTED_WITH_OTHER_USER(
      "kulalnıcı başka çalışanlar ile bağlı", "user_connected_with_other_user"),
  USER_CONNECTED_WITH_VACATION(
      "kulalnıcının bağlı olduğu istekler bulunmakta", "user_connected_with_vacation"),
  USER_CONNECTED_WITH_EXPENSE(
      "kulalnıcının bağlı olduğu istekler bulunmakta", "user_connected_with_expense");

  private final String message;
  private final String key;

  MessageUtil(String message, String key) {
    this.message = message;
    this.key = key;
  }

  public String getMessage() {
    return this.message;
  }

  public String getKey() {
    return this.key;
  }

  @Override
  public String toString() {
    return message;
  }
}
