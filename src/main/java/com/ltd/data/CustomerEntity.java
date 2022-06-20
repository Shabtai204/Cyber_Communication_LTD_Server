package com.ltd.data;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Customer")
public class CustomerEntity {
	@Id
	private String id;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@NotEmpty
	private String country;
	@NotEmpty
	private String city;
	@NotEmpty
	private String street;
	@NotEmpty
	private String streetNumber;
	@NotEmpty
	private String postalCode;
	@NotEmpty
	private String internetBundle;
	@NotEmpty
	private String sector;
	@NotEmpty
	private String creditCard;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getInternetBundle() {
		return internetBundle;
	}

	public void setInternetBundle(String internetBundle) {
		this.internetBundle = internetBundle;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, country, creditCard, firstName, id, internetBundle, lastName, postalCode, sector,
				street, streetNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerEntity other = (CustomerEntity) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(creditCard, other.creditCard) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(internetBundle, other.internetBundle)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(postalCode, other.postalCode)
				&& Objects.equals(sector, other.sector) && Objects.equals(street, other.street)
				&& streetNumber == other.streetNumber;
	}
}
