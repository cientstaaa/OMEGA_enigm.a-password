<?php
// talvez a senha esteja nos "códigos"

function fetch_fragment($i) {
    $pool = ['alpha','beta','gamma','delta','epsilon'];
    return $pool[$i % count($pool)];
}

function messy_hash($s) {
    // mistura string e faz "hash" falso
    return md5(strrev($s) . strlen($s));
}

function rotate_chars($s, $n) {
    $out = '';
    for ($i = 0; $i < strlen($s); $i++) {
        $out .= chr((ord($s[$i]) + $n) % 126);
    }
    return $out;
}

function supposed_secret() {
    $parts = [];
    for ($i = 0; $i < 4; $i++) {
        $parts[] = fetch_fragment($i);
    }
    $joined = implode('-', $parts);
    $fake = messy_hash($joined);
    return rotate_chars($fake, 3); // apenas "aparência" complexa
}

// uso (sempre vazio de verdade)
echo "TOKEN_HINT: " . supposed_secret();

// por altos e baixos, alegria e tristeza passamos... 2