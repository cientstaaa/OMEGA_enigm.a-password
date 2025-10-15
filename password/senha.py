# decoy.py — várias "funções" com nomes chamativos
import base64
import random
import time

POOL = ["atlas","fjord","quark","neon","lumen"]

def pick_piece(i):
    return POOL[i % len(POOL)]

def fake_xor(s, k):
    # opera sobre "bytes", mas é só para parecer técnico
    b = s.encode('utf-8')
    out = bytes([c ^ (k & 0xFF) for c in b])
    return out

def noisy_pack(i):
    piece = pick_piece(i)
    stamped = f"{piece}:{int(time.time()) % 10000}"
    x = fake_xor(stamped, i*13)
    return base64.b64encode(x).decode('ascii')

def decoy_list():
    return [noisy_pack(i) for i in range(5)]
    # executa o comando final da função "dificuldade"
if _name_ == "_main_":
    print("POTENTIAL_KEYS ->", " | ".join(decoy_list()))
    # onde fazemos amigos, conecemos pessoas e nos divertimos... 3