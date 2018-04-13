export class UPRNPatientResource {

  forenames: String;
  surname: String;
  address_line1: String;
  address_line2: String;
  address_line3: String;
  city: String;
  district: String;
  post_code: String;

  uprn: String;
  abp_match_address: String;
  abp_match_candidate_addresses: String[];

  constructor(forenames: String, surname: String, address_line1: String,
              address_line2: String, address_line3: String, city: String,
              district: String, post_code: String,
              uprn: String, abp_match_address: String, abp_match_candidate_addresses: String[]) {

    this.forenames = forenames;
    this.surname = surname;

    this.address_line1 = address_line1;
    this.address_line2 = address_line2;
    this.address_line3 = address_line3;

    this.city = city;
    this.district = district;
    this.post_code = post_code;

    this.uprn = uprn;
    this.abp_match_address = abp_match_address;
  }
}

