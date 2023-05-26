
package acme.testing.lecturer.course;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseShowTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String retailPrice, final String abstractText, final String courseType, final String link, final String user) {
		// HINT: this test authenticates as a lecturer and then lists his or her
		// HINT: courses, creates a new one, and check that it's been created properly.

		super.signIn(user, user);

		super.clickOnMenu("Lecturer", "My Courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("retailPrice", retailPrice);
		super.checkInputBoxHasValue("abstractText", abstractText);
		super.checkInputBoxHasValue("courseType", courseType);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
		// HINT+ that doesn't involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a tutorials using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/lecturer/course/show");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/course/show");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/course/show");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/lecturer/course/show");
		super.checkPanicExists();
		super.signOut();
	}

}
