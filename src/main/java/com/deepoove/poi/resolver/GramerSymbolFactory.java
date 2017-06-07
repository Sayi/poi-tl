package com.deepoove.poi.resolver;

public class GramerSymbolFactory {

	public static GramerSymbol getGramerSymbol(String tag) {
		char symbol = tag.charAt(0);
		switch (symbol) {
		case '@':
			return GramerSymbol.IMAGE;
		case '#':
			return GramerSymbol.TABLE;
		default:
			return GramerSymbol.TEXT;
		}
	}

}
