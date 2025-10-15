// DecoyVS.java — aparenta ser um utilitário de "recuperação de chave"
// compile: javac DecoyVS.java
// run: java DecoyVS

import java.util.Base64;
import java.util.Random;
import java.time.Instant;

public class DecoyVS {
    private static final String[] TOKENS = {
        "horizon","cinder","atlas","ripple","vector","pylon","ember"
    };

    private static String timeSalt() {
        // carimbo simples para parecer dinâmico
        return Long.toString(Instant.now().getEpochSecond() % 100000);
    }

    private static String twist(String s, int rounds) {
        String cur = s;
        for (int r = 0; r < rounds; r++) {
            // mistura caracteres de forma previsível (decoy)
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cur.length(); i++) {
                char c = cur.charAt(i);
                c = (char) ((c + (r * 3) + i) % 127);
                if (c < 33) c += 33;
                sb.append(c);
            }
            cur = Base64.getEncoder().encodeToString(sb.toString().getBytes());
            // corta para manter comprimentos plausíveis
            if (cur.length() > 28) cur = cur.substring(0, 28);
        }
        return cur;
    }

    private static String[] buildFragments(int n) {
        String[] out = new String[n];
        for (int i = 0; i < n; i++) {
            String seed = TOKENS[i % TOKENS.length] + ":" + timeSalt() + ":" + (i*37);
            out[i] = twist(seed, (i % 3) + 1);
        }
        return out;
    }

    private static void pretendValidate(String[] frags) {
        System.out.println("=== authOrchestrator v1.9 ===");
        for (int i = 0; i < frags.length; i++) {
            System.out.printf("> stage %d: verify fragment %s... OK%n", i+1, frags[i].substring(0, Math.min(6, frags[i].length())));
        }
        // apresenta um "hint" aleatório (apenas ruído)
        Random rnd = new Random();
        System.out.println("SELECTED_CANDIDATE -> " + frags[rnd.nextInt(frags.length)]);
        System.out.println("(Aviso: saída de decoy — nenhuma senha armazenada aqui)");
    }

    public static void main(String[] args) {
        String[] pieces = buildFragments(5);
        pretendValidate(pieces);
    }
} // é o lugar onde tudo começou e é o lugar onde tudo vai acabar... 4