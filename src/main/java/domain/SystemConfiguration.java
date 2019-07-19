
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	// Attributes
	private String				nameSystem;
	private String				bannerUrl;
	private String				welcomeMessageEnglish;
	private String				welcomeMessageSpanish;
	private String				phoneCountryCode;
	private Collection<String>	creditCardBrands;
	private Collection<String>	voidWords;


	// Getters and Setters
	@NotBlank
	public String getNameSystem() {
		return this.nameSystem;
	}

	public void setNameSystem(final String nameSystem) {
		this.nameSystem = nameSystem;
	}

	@NotBlank
	@URL
	public String getBannerUrl() {
		return this.bannerUrl;
	}

	public void setBannerUrl(final String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	@NotBlank
	public String getWelcomeMessageEnglish() {
		return this.welcomeMessageEnglish;
	}

	public void setWelcomeMessageEnglish(final String welcomeMessageEnglish) {
		this.welcomeMessageEnglish = welcomeMessageEnglish;
	}

	@NotBlank
	public String getWelcomeMessageSpanish() {
		return this.welcomeMessageSpanish;
	}

	public void setWelcomeMessageSpanish(final String welcomeMessageSpanish) {
		this.welcomeMessageSpanish = welcomeMessageSpanish;
	}

	@NotBlank
	@Pattern(regexp = "^[+][1-9]\\d{0,2}$")
	public String getPhoneCountryCode() {
		return this.phoneCountryCode;
	}

	public void setPhoneCountryCode(final String phoneCountryCode) {
		this.phoneCountryCode = phoneCountryCode;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@NotEmpty
	@EachNotBlank
	public Collection<String> getCreditCardBrands() {
		return this.creditCardBrands;
	}

	public void setCreditCardBrands(final Collection<String> creditCardBrands) {
		this.creditCardBrands = creditCardBrands;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@NotEmpty
	@EachNotBlank
	public Collection<String> getVoidWords() {
		return this.voidWords;
	}

	public void setVoidWords(final Collection<String> voidWords) {
		this.voidWords = voidWords;
	}

}
