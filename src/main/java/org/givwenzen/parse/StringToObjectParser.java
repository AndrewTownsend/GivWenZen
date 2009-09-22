package org.givwenzen.parse;

import java.util.ArrayList;
import java.util.List;

public class StringToObjectParser {

   MethodParameterParser[] parserInDesiredOrder = new MethodParameterParser[3];

   public StringToObjectParser() {
      parserInDesiredOrder[0] = new StringParser();
      parserInDesiredOrder[1] = new PropertyEditorParser();
      parserInDesiredOrder[2] = new ValueObjectParser();
   }

   public Object[] convertParamertersToTypes(Object[] params, Class[] paramTypes) throws Exception {

      List convertedParams = new ArrayList();
      for (int index = 0; index < paramTypes.length; index++) {
         convertedParams.add(convertStringToParamType(params[index], paramTypes[index]));
      }
      return convertedParams.toArray(new Object[convertedParams.size()]);
   }

   private Object convertStringToParamType(Object param, Class<?> paramType) throws Exception {

      for (MethodParameterParser parser : parserInDesiredOrder) {
         if (parser.canParse(paramType)) return parser.parse(param, paramType);
      }
      return param;
   }

}