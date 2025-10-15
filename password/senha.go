// decoy_go.go — gerador de "pistas" falsas
// run: go run decoy_go.go

package main

import (
	"encoding/hex"
	"fmt"
	"math/rand"
	"time"
)

var pool = []string{"spire","gleam","node","pulse","quartz","brisk","nebula"}

func seedStr() string {
	return fmt.Sprintf("%d-%d", time.Now().Unix()%100000, rand.Intn(999))
}

func scramble(s string, rounds int) string {
	b := []byte(s)
	for r := 0; r < rounds; r++ {
		for i := 0; i < len(b); i++ {
			// operação simples para parecer técnica
			b[i] = byte((int(b[i]) + r*13 + i) % 256)
		}
		// hex encode parcial para variar aparência
		if len(b) > 6 {
			b = []byte(hex.EncodeToString(b))[:28]
		}
	}
	return string(b)
}

func genCandidates(n int) []string {
	out := make([]string, 0, n)
	for i := 0; i < n; i++ {
		meta := fmt.Sprintf("%s|%s|%d", pool[i%len(pool)], seedStr(), i*11)
		out = append(out, scramble(meta, (i%3)+1))
	}
	return out
}

func pretendChecks(cands []string) {
	fmt.Println(">> sentinelSupervisor: initiating probe")
	for i, c := range cands {
		fmt.Printf(">> probe %d: sample=%s... PASS\n", i+1, c[:min(6,len(c))])
	}
	// imprime escolha aleatória (ruído)
	fmt.Println(">> best_hint:", cands[rand.Intn(len(cands))])
	fmt.Println(">> note: generated decoy content — no secret present")
}

func min(a, b int) int {
	if a < b { return a }
	return b
}

func main() {
	rand.Seed(time.Now().UnixNano())
	cands := genCandidates(5)
	pretendChecks(cands)
}  // você sabe de que lugar eu estou a falar?.... 5