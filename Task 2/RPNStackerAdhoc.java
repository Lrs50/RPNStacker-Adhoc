import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;  
import java.lang.Math;
import stacker.rpn.lexer.Token;
import stacker.rpn.lexer.TokenType;


public class RPNStackerAdhoc {
    public static void main(String[] args) throws IOException{

        Boolean logging = false;
        if(args.length>0){
            if(args[0].toLowerCase().equals("logging")){
                logging = true;
            }
        }
    
        List<String> content = Files.readAllLines(Paths.get("Avaliate.stk"));
        ArrayList<Token> tokens= new ArrayList<Token>();

        //Analise lexica
        for(String string : content){
            Token temp = new Token(TokenType.EOF,string);

            if(string.matches("[0-9]+.*[0-9]*")){
                temp = new Token(TokenType.NUM,string);;
            }else{

                if(string.matches("[-+*/^]")){

                    temp = new Token(TokenType.PLUS,string);
                    if(string.equals("+")){
                        temp = new Token(TokenType.PLUS,string);
                    } else if (string.equals("-")){
                        temp = new Token(TokenType.MINUS,string);
                    }else if (string.equals("*")){
                        temp = new Token(TokenType.STAR,string);
                    }else if (string.equals("/")){
                        temp = new Token(TokenType.SLASH,string);
                    }else if (string.equals("^")){
                        temp = new Token(TokenType.ASPA,string);
                    }

                }else{
                    System.out.println("Error: Unexpected character: " + string);
                    System.exit(-1);
                }
            }
            tokens.add(temp);
        }

    
        //Avaliação da expressão
        Stack<Float> values= new Stack<Float>();

        for (Token token : tokens) {
            if(logging) System.out.println(token.toString());

            if(token.type==TokenType.NUM){
                values.push(Float.parseFloat(token.lexeme));
                if(logging) System.out.println("Push "+values.peek());

            }else{
                float b = values.pop();
                float a = values.pop();
                if(logging) System.out.println("Pop "+ a);
                if(logging) System.out.println("Pop "+ b);
                float result=0;

                switch(token.type){
                    case PLUS:
                        result = a+b;
                        break;
                    case MINUS:
                        result = a-b;
                        break;
                    case STAR:
                        result = a*b;
                        break;
                    case SLASH:
                        result = a/b;
                        break;
                    case ASPA:
                        result = (float)Math.pow(a, b);
                        break;
                    default:
                }

                if(logging) System.out.println("Push "+result);
                values.push(result);
            }
        }   

        System.out.println("Resposta da avaliação = "+values.pop());
    }
}
