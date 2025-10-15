// decoy.js — funções encadeadas que parecem "decifrar" algo
function grabMeta(idx) {
  const bank = ['tree','river','stone','cloud','moon'];
  return bank[idx % bank.length];
}

function fauxDecode(str) {
  // inverte, junta com timestamp e retorna base64 (decoy)
  const t = Date.now().toString().slice(-4);
  return btoa(str.split('').reverse().join('') + '|' + t);
}

function transform(x) {
  return fauxDecode(grabMeta(x) + '_' + x);
}

function reveal() {
  let arr = [];
  for (let i = 0; i < 5; i++) {
    arr.push(transform(i));
  }
  // mostra algo que parece "chave"
  console.log('REVEAL_CANDIDATES:', arr.join(' '));
  // também inserir no DOM para confundir
  const el = document.getElementById('hint');
  if (el) el.textContent = arr[Math.floor(Math.random() * arr.length)];
}

// chama automaticamente (decoy)
reveal();

// um lugar que desde criança estamos juntos... 1