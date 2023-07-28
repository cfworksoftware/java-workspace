package webSites;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class InvestmentWebsitesEnumTest {


	List<Integer> listDuplicateUsingCollectionsFrequency(List<Integer> list) {
	    List<Integer> duplicates = new ArrayList<>();
	    Set<Integer> set = list.stream()
	    		.filter(i -> Collections.frequency(list, i) > 1)
	    		.collect(Collectors.toSet());
	    duplicates.addAll(set);
	    return duplicates;
	}

	@Test
	void testKeyNotNullNorEmpty() {
		for(InvestmentWebsitesEnum test: InvestmentWebsitesEnum.values()) {
//			assertNotEquals (test.getKey(),null, test.name()+ " key is null");			
			assertAll("Key not null nor empty",
					()->assertNotEquals(test.getKey(),null, test.name()+ " key is null"),
					()->assertNotEquals(test.getKey(),"",test.name() + "key is empty")
					);
		}		
	}

	@Test
	void testNoDuplicateKey() {
		List<Integer> keys = new ArrayList<Integer>();	
		for(InvestmentWebsitesEnum test: InvestmentWebsitesEnum.values()) {
			keys.add(test.getKey());
//			System.out.println(keys);
		}
	    List<Integer> duplicates = listDuplicateUsingCollectionsFrequency(keys);
//	    System.out.println(duplicates);
	    assertEquals(duplicates.size(), 0,"Duplicate key found");	
	}
	
	@Test
	void testShareNameNotNullNorEmpty() {
		for(InvestmentWebsitesEnum test: InvestmentWebsitesEnum.values()) {
			assertAll("Sharename contains a string",
					()->assertNotEquals(test.getShareName(),null, test.name()+ " Sharename is null"),
					()->assertNotEquals(test.getShareName(),"",test.name() + "Sharename string is empty")
					);
		}
	}
	
	@Test
	void testWebURLNotNullNorEmpty() {
		for(InvestmentWebsitesEnum test: InvestmentWebsitesEnum.values()) {
			assertAll("Sharename contains a string",
					()->assertNotEquals(test.getWebURL(),null, test.name()+ "web url is null"),
					()->assertNotEquals(test.getWebURL(),"",test.name() + "web url string is empty")
					);
		}
	}	
}
