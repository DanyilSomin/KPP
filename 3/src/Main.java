import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.nio.charset.StandardCharsets;
 
class Main
{
    public static void main(String[] args) throws IOException
    {
        if (args[0].equals("0"))
        { 
            File file = new File("../text.txt");
    
            String fileContent = new String();

            try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8.name()))
            {
                while (sc.hasNextLine())
                {
                    fileContent += sc.nextLine();
                    fileContent += "\n";
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            ArrayList<Formula> formulas = new ArrayList<Formula>();

            Formula tmpFormula;
            for (int i = 0; i < fileContent.length();)
            {
                if (i != 0 && fileContent.charAt(i) != ' ' && fileContent.charAt(i) != '\n')
                {
                    ++i;
                    continue;
                }

                if (i != 0) ++i;

                tmpFormula = new Formula();
                tmpFormula.mStart = i;

                while (true)
                {
                    if (i == fileContent.length()
                            || fileContent.charAt(i) == ' '
                            || fileContent.charAt(i) == '\n')
                    {
                        tmpFormula.mEnd = i - 1;
                        if (tmpFormula.valid())
                        {
                            formulas.add(new Formula(tmpFormula));
                            tmpFormula = new Formula();
                        }

                        break;
                    }

                    if (Character.isDigit(fileContent.charAt(i)))
                    {
                        if (i > 0 && fileContent.charAt(i - 1) == ')')
                        {
                            break;
                        }
                        if (i > 0 && Character.isLetter(fileContent.charAt(i - 1)))
                        {
                            break;
                        }

                        ++i;
                        continue;
                    }

                    if (fileContent.charAt(i) == '/')
                    {
                        if (i > 0 && fileContent.charAt(i - 1) == '(')
                        {
                            break;
                        }

                        ++i;
                        continue;
                    }

                    if (fileContent.charAt(i) == '*')
                    {
                        if (i > 0 && fileContent.charAt(i - 1) == '(')
                        {
                            break;
                        }
                        
                        ++i;
                        continue;
                    }

                    if (fileContent.charAt(i) == '(')
                    {
                        if (i > 0 && Character.isDigit(fileContent.charAt(i - 1)))
                        {
                            break;
                        }
                        if (i > 0 && Character.isLetter(fileContent.charAt(i - 1)))
                        {
                            break;
                        }

                        tmpFormula.mOpenCount += 1;
                        ++i;
                        continue;
                    }
                    if (fileContent.charAt(i) == ')')
                    {
                        if (i > 0 && fileContent.charAt(i - 1) == '*')
                        {
                            break;
                        }
                        if (i > 0 && fileContent.charAt(i - 1) == '/')
                        {
                            break;
                        }
                        
                        tmpFormula.mCloseCount += 1;
                        ++i;
                        continue;
                    }
                    if (Character.isLetter(fileContent.charAt(i)))
                    {
                        if (i > 0 && Character.isLetter(fileContent.charAt(i - 1)))
                        {
                            break;
                        }

                        tmpFormula.mVars.add(i);
                        ++i;
                        continue;
                    }

                    break;
                }
            }

            ArrayList<Character> varSet = new ArrayList<>();
            for (Formula f : formulas)
            {
                for (int i : f.mVars)
                {
                    if (!varSet.contains(fileContent.charAt(i)))
                    {
                        varSet.add(fileContent.charAt(i));
                    }
                }
            }

            Random r = new Random();

            StringBuilder outStr = new StringBuilder(fileContent);

            System.out.println(fileContent);    
            
            if (varSet.size() > 0)
            for (Formula f : formulas)
            {
                for (int i : f.mVars)
                {
                    outStr.setCharAt(i, varSet.get(r.nextInt(varSet.size())));
                }
            }

            for (char ch : varSet)
            {
                System.out.println(ch);
            }

            System.out.println(outStr.toString());
        }
        else if (args[0].equals("1"))
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
            System.out.println(doTask1(reader.readLine()));
        }
        else if (args[0].equals("test"))
        {
            Test.doTest();
        }
    }

    public static String doTask1(String str)
    {
        String ret = "Error";

        String[] sentences = str.split("\\.", 2);

        if (sentences.length < 2) return ret;

        String[] words = sentences[0].split(" ");

        for (String w : words)
        {
            Pattern pat = Pattern.compile("(^|\\s|\\.)" + w + "(\\.|\\s|$)");
            Matcher matcher = pat.matcher(sentences[1]);
            if (!matcher.find())
            {
                return new String(w);
            } 
        }

        return new String(ret);
    }
}