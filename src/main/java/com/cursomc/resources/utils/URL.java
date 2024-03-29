package com.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	/**
	 * Recebe uma lista de string e retorna um lista de ids em long
	 * @param s
	 * @return
	 */
	public static List<Long> decodeLongList(String s){
		String [] vet = s.split(",");
		List<Long> list = new ArrayList<>();
		for(int i=0; i<vet.length; i++) {
			list.add(Long.parseLong(vet[i]));
		}
		return list;
		
		// recurso java 8 essa linha faz todo o código acima
//		return Arrays.asList(s.split(",")).stream().map(x -> Long.parseLong(x)).collect(Collectors.toList());
	}

}
