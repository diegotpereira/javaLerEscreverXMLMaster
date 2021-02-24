package br.com.java;

import java.io.File;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.w3c.dom.Element;


public class Main {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Scanner entrada = new Scanner(System.in);
		 System.out.println("Entre com um número entre 1 ou 2:");
		 int num = entrada.nextInt();
		 switch (num) {
	     case 1:
	    	 lerXML();
	       break;
	     case 2:
	    	 escreverXML();
	       break;
	     default:
	       System.out.println("Número inválido");
	  }
		
		
	}
	private static void lerXML() throws Exception{
		File fXmlFile = new File("C:\\Users\\administrator\\Documents\\Agenda.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		System.out.println("Root do elemento: " + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("guest");
		
		System.out.println("----------------------------");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			//System.out.println("\nElemento corrente :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				System.out.println("Id : " + (temp+1));
				System.out.println("Primeiro nome.: " + eElement.getElementsByTagName("fname").item(0).getTextContent());
				System.out.println("Segundo nome..: " + eElement.getElementsByTagName("lname").item(0).getTextContent());
			}
		}
	}
	
	public static void escreverXML() throws Exception{
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.newDocument();
	        
	        Element root = doc.createElementNS("zetcode.com", "users");
	        doc.appendChild(root);

	        root.appendChild(criarUsuario(doc, "1", "Robert", "Brown", "programador"));
	        root.appendChild(criarUsuario(doc, "2", "Pamela", "Kyle", "escritor"));
	        root.appendChild(criarUsuario(doc, "3", "Peter", "Smith", "professor"));

	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transf = transformerFactory.newTransformer();
	        
	        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transf.setOutputProperty(OutputKeys.INDENT, "yes");
	        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	        
	        DOMSource source = new DOMSource(doc);

	        File myFile = new File("C:\\Users\\administrator\\Documents\\escrever.xml");
	        
	        StreamResult console = new StreamResult(System.out);
	        StreamResult file = new StreamResult(myFile);

	        transf.transform(source, console);
	        transf.transform(source, file);
	    }

	    private static Node criarUsuario(Document doc, String id, String nome, 
	            String sobrenome, String ocupacao) {
	        
	        Element usuario = doc.createElement("usuario");

	        usuario.setAttribute("id", id);
	        usuario.appendChild(criarUsuarioElement(doc, "nome", nome));
	        usuario.appendChild(criarUsuarioElement(doc, "sobrenome", sobrenome));
	        usuario.appendChild(criarUsuarioElement(doc, "ocupacao", ocupacao));

	        return usuario;
	    }

	    private static Node criarUsuarioElement(Document doc, String name, 
	            String value) {

	        Element node = doc.createElement(name);
	        node.appendChild(doc.createTextNode(value));

	        return node;
	    }
	}
