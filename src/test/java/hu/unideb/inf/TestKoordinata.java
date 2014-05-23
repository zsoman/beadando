package hu.unideb.inf;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestKoordinata {

	@Test
	public void testEquals() {
		Koordinatak a = new Koordinatak(1, 1);
		Koordinatak b = new Koordinatak(1, 1);
		assertEquals(true, a.equals(b));
		b = new Koordinatak(0, 0);
		assertEquals(false, a.equals(b));
		b = new Koordinatak(1, 0);
		assertEquals(false, a.equals(b));
		b = new Koordinatak(0, 1);
		assertEquals(false, a.equals(b));
		assertEquals(false, a.equals(new Object()));
		b = null;
		assertEquals(false, a.equals(b));
	}

	@Test
	public void testToString() {
		Koordinatak a = new Koordinatak(0, 0);
		String s = "( 0, 0 )";
		assertEquals(s, a.toString());
	}

}
