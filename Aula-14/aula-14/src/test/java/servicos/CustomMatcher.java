package servicos;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CustomMatcher extends TypeSafeMatcher<String> {
	   
	   private String letter;
	   
	   private CustomMatcher(String letter) {
		   this.letter = letter;
	   }

	   public void describeTo(Description description) {
	      description.appendValue("Esperava uma palavra que começa com " + this.letter);
	   }

	   @Override
	   protected boolean matchesSafely(String item) {
		   String letra = String.valueOf(item.charAt(0));
		   return letra.equals(this.letter);
	   }

	   public static CustomMatcher startWithLetter(String letter) {
	      return new CustomMatcher(letter);
	   }
	}