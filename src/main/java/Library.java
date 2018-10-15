/*
This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org>
 */

import com.sun.xml.internal.ws.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Library
{
    private BigDecimal givenNumber;
    private String telikosArithmos;
    private String dekadikoMeros = "";
    private String akeraioMeros = "";
    private int intLength;
    private int decLength;
    private BigDecimal intPart;
    private BigDecimal decimalPart;
    private BigDecimal[] dividedArray;

    public String getNumberInWords()
    {
        return telikosArithmos;
    }

    private HashMap<BigDecimal, String> numberMap = new HashMap<>();
    private HashMap<Integer, String> decadesMap = new HashMap<>();
    private ArrayList<String> katataksi = new ArrayList<>(); // ??

    public boolean someLibraryMethod()
    {

        return true;
    }

    public void init(BigDecimal aNumber)
    {
        decadesMap.put(0, "");
        decadesMap.put(1, "χιλιάδες");
        decadesMap.put(2, "εκατομμύρια");
        decadesMap.put(3, "δισεκατομμύρια");
        decadesMap.put(4, "τρισεκατομμύρια");
        decadesMap.put(5, "τετράκις εκατομμύρια");
        decadesMap.put(6, "πεντάκις εκατομμύρια");
        decadesMap.put(7, "εξάκις εκατομμύρια");

        numberMap.put(new BigDecimal(0), "μηδέν");
        numberMap.put(new BigDecimal(1), "ένα");
        numberMap.put(new BigDecimal(2), "δύο");
        numberMap.put(new BigDecimal(3), "τρία");
        numberMap.put(new BigDecimal(4), "τέσσερα");
        numberMap.put(new BigDecimal(5), "πέντε");
        numberMap.put(new BigDecimal(6), "έξι");
        numberMap.put(new BigDecimal(7), "επτά");
        numberMap.put(new BigDecimal(8), "οχτώ");
        numberMap.put(new BigDecimal(9), "εννέα");
        numberMap.put(new BigDecimal(10), "δέκα");
        numberMap.put(new BigDecimal(20), "είκοσι");
        numberMap.put(new BigDecimal(30), "τριάντα");
        numberMap.put(new BigDecimal(40), "σαράντα");
        numberMap.put(new BigDecimal(50), "πενίντα");
        numberMap.put(new BigDecimal(60), "εξήντα");
        numberMap.put(new BigDecimal(70), "εβδομήντα");
        numberMap.put(new BigDecimal(80), "ογδόντα");
        numberMap.put(new BigDecimal(90), "ενενήντα");
        numberMap.put(new BigDecimal(100), "εκατόν");
        numberMap.put(new BigDecimal(200), "διακόσια");
        numberMap.put(new BigDecimal(300), "τριακόσια");
        numberMap.put(new BigDecimal(400), "τετρακόσια");
        numberMap.put(new BigDecimal(500), "πεντακόσια");
        numberMap.put(new BigDecimal(600), "εξακόσια");
        numberMap.put(new BigDecimal(700), "εφτακόσια");
        numberMap.put(new BigDecimal(800), "οχτακόσια");
        numberMap.put(new BigDecimal(900), "ενιακόσια");

        givenNumber = aNumber;

        givenNumber = givenNumber.stripTrailingZeros();
        givenNumber = givenNumber.setScale(2,RoundingMode.DOWN);
        intLength = givenNumber.precision() - givenNumber.scale();
        intPart = givenNumber.setScale(0, RoundingMode.DOWN);
        decimalPart = givenNumber.remainder(BigDecimal.ONE);


//Debug info
//        System.out.println("int part is " + intPart);
//        System.out.println("decimal part is " + decimalPart);
//        System.out.println("total length is " + givenNumber.precision());
//        System.out.println("int length is " + (givenNumber.precision() - givenNumber.scale()));
//        System.out.println("decimal length is " + givenNumber.scale());

    }

    public void split()
    {
        dividedArray = new BigDecimal[((intLength) / 3) + 1];
        int power = (intLength - 1) / 3 * 3;

        //Ακέραιο μερος
        if (intLength == 0) //Dirty fix
        {
            akeraioMeros = "μηδέν";
        }
        else
        {
            for (int i = 0; i < (intLength - 1) / 3 + 1; i++)
            {
                dividedArray[i] = intPart.divide(BigDecimal.TEN.pow(power), 0, RoundingMode.DOWN);

                intPart = intPart.remainder(BigDecimal.TEN.pow(power));
                power = power - 3;
                akeraioMeros += " " + (splitUnits(dividedArray[i]) + " " + decadesMap.get(((power / 3) + 1)));

            }
        }


        //Δεκαδικό μέρος

        if (decimalPart.scale() > 0) //Check for decimal part
        {

            String[] s1 = String.valueOf(decimalPart).split("\\."); // Get decimal part




            decimalPart = new BigDecimal(s1[1]);
            decLength = decimalPart.precision() - decimalPart.scale();
            power = (decLength - 1) / 3 * 3;

            Arrays.fill(dividedArray, null); //Nullify array

            for (int i = 0; i < (decLength - 1) / 3 + 1; i++)
            {
                dividedArray[i] = decimalPart.divide(BigDecimal.TEN.pow(power), 0, RoundingMode.DOWN);

                decimalPart = decimalPart.remainder(BigDecimal.TEN.pow(power));
                power = power - 3;
                dekadikoMeros += " και " + (splitUnits(dividedArray[i]) + " " + decadesMap.get(((power / 3) + 1)));
            }

        }

        if (!dekadikoMeros.isEmpty()) dekadikoMeros += " λεπτά";

        telikosArithmos = akeraioMeros + " ευρώ " + dekadikoMeros;
        telikosArithmos = telikosArithmos.replace("δέκα δύο", "δώδεκα");//Dirty fix
        telikosArithmos = telikosArithmos.replace("δέκα ένα", "έντεκα");
        telikosArithmos = telikosArithmos.replaceAll("\\s+", " ");
        telikosArithmos = telikosArithmos.trim(); //Trim leading and trailing spaces
        telikosArithmos = StringUtils.capitalize(telikosArithmos); //Capitalize first letter
        System.out.println(telikosArithmos);

    }

    public String splitUnits(BigDecimal number)
    {
        BigDecimal ekatontades = number.divide(new BigDecimal(100), 0, RoundingMode.DOWN);
        BigDecimal upolipo = number.remainder(new BigDecimal(100));
        BigDecimal dekades = upolipo.divide(new BigDecimal(10), 0, RoundingMode.DOWN);
        upolipo = upolipo.remainder(new BigDecimal(10));
        BigDecimal monades = upolipo;
        String ekatontadesString = "";
        String dekadesString = "";
        String monadesString = "";

        if (ekatontades.compareTo(BigDecimal.ZERO) != 0)
        {
            ekatontadesString = numberMap.get(ekatontades.multiply(new BigDecimal(100))) + " ";
        }
        if (dekades.compareTo(BigDecimal.ZERO) != 0)
        {
            dekadesString = numberMap.get(dekades.multiply(new BigDecimal(10))) + " ";
        }
        if (monades.compareTo(BigDecimal.ZERO) != 0)
        {
            monadesString = numberMap.get(monades);
        }

        return ekatontadesString + dekadesString + monadesString;
    }

}
