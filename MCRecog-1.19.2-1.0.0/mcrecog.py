import speech_recognition
import pyttsx3

import mc_socket
from mc_socket import MCSocket
import speech_recognition as sr
import argparse



parser = argparse.ArgumentParser()
parser.add_argument("-m", "--Microphone", help="Manually select microphone", action='store_true')
arguments = parser.parse_args()

r = sr.Recognizer()
r.energy_threshold = 300

mic_idx = None
if arguments.Microphone:
    for index, name in enumerate(sr.Microphone.list_microphone_names()):
        print(f"Microfone com o nome {name} encontrado para Microphone(device_index={index})")

    mic_idx = int(input("Por favor, indique o index do dispositivo para selecionar seu microfone primario: "))

mic = sr.Microphone(mic_idx)
mc = MCSocket(7777)
print("Pronto para iniciar o reconhecimento de voz!")

engine = pyttsx3.init()

def get_response(response):
    res = response
    response = str(resp).replace(" ", "").lower()
    print(response)

    ret = []
    if "teste" in response:
        ret.append("Explode and die")
        engine.say("Explodindo")
        engine.runAndWait()
    if "carro" in response:
        ret.append("Lose 10 arrows")
        engine.say("Perdendo 10 flechas")
        engine.runAndWait()
    if "urso" in response:
        ret.append("Spawn 7 hostile polar bears")
        engine.say("Spawnando 7 ursos")
        engine.runAndWait()
    if "espada" in response:
        ret.append("Axolotl time")
        engine.say("efeito Axolote")
        engine.runAndWait()
    if "bosta" in response:
        ret.append("Spawn 10 zombies")
        engine.say("Spawnando 10 zumbis")
        engine.runAndWait()
    if "merda" in response:
        ret.append("Spawn 10 skeletons")
        engine.say("Spawnando 10 esqueletos")
        engine.runAndWait()
    if "porco" in response:
        ret.append("Lose all hunger")
        engine.say("Perdendo toda a fome")
        engine.runAndWait()
    if "teclado" in response:
        ret.append("Lose something random")
        engine.say("Perdendo algo aleatório")
        engine.runAndWait()
    if "chinelo" in response:
        ret.append("Spawn 7 creepers")
        engine.say("Spawnando 7 creepers")
        engine.runAndWait()
    if "bolsa" in response:
        ret.append("Spawn 7 blazes")
        engine.say("Spawnando 7 blazes")
        engine.runAndWait()
    if "guitarra" in response:
        ret.append("Spawn 10 angry endermen")
        engine.say("Spawnando 10 endernmans bravos")
        engine.runAndWait()
    if "inferno" in response:
        ret.append("Spawn 7 wither skeletons")
        engine.say("Spawnando 7 wither skeletons")
        engine.runAndWait()
    if "caverna" in response:
        ret.append("Mining fatigue")
        engine.say("Fadiga de mineração")
        engine.runAndWait()
    if 'gato' in response:
        ret.append("Big hole")
        engine.say("Buraco no chão")
        engine.runAndWait()
    if 'dia' in response:
        ret.append("Set time to night")
        engine.say("Ficando de noite")
        engine.runAndWait()
    if 'cama' in response:
        ret.append("Spawn 7 phantoms")
        engine.say("Spawnando 7 phantoms")
        engine.runAndWait()
    if 'barco' in response:
        ret.append("Fill inventory with boats")
        engine.say("Enchendo inventário com barcos")
        engine.runAndWait()
    if 'dragão' in response:
        ret.append("Play dragon noise, spawn 10 endermite")
        engine.say("spawnando 10 endermite")
        engine.runAndWait()
    if 'twitch' in response:
        ret.append("Spawn supercharged creeper")
        engine.say("Spawnar super creeper")
        engine.runAndWait()
    if 'carvão' in response:
        ret.append("Set on fire")
        engine.say("Pegando fogo")
        engine.runAndWait()
    if 'ferro' in response:
        ret.append("Spawn aggro iron golem")
        engine.say("Spawnando iren golem")
        engine.runAndWait()
    if 'ouro' in response:
        ret.append("Spawn pigmen")
        engine.say("Spawnando pigmen")
        engine.runAndWait()
    if 'diamante' in response:
        ret.append("Set to half a heart")
        engine.say("Metade de um coração")
        engine.runAndWait()
    if 'mod' in response:
        ret.append("Shuffle inventory")
        engine.say("Embaralhar inventário")
        engine.runAndWait()
    if 'agora' in response:
        ret.append("Teleport randomly")
        engine.say(Teletransportando")
        engine.runAndWait()
    if 'água' in response:
        ret.append("In water")
        engine.say("Água")
        engine.runAndWait()
    if 'bloco' in response:
        ret.append("Spawn killer rabbits")
        engine.say("Spawnando coelhos assassinos")
        engine.runAndWait()
    if 'altura' in response:
        ret.append("Launched in the air")
        engine.say("Lançado para o ar")
        engine.runAndWait()
    if 'craft' in response:
        ret.append("Surround in obsidian")
        engine.say("Preso em obisidian")
        engine.runAndWait()
    if 'vila' in response:
        ret.append("Spawn witches")
        engine.say("Spawnando bruxas")
        engine.runAndWait()
    if 'bomba' in response:
        ret.append("Give something useless")
        engine.say("Recebendo algo inútil")
        engine.runAndWait
    if 'livro' in response:
        ret.append("Random explosion")
        engine.say("Explosão aleatória") 
        engine.runAndWait()
    if 'luz' in response:
        ret.append("Lightning")
        engine.say("caindo raios")
        engine.runAndWait()
    if 'tinta' in response:
        ret.append("Ink Splat")
        engine.say("Tinta")
        engine.runAndWait()
    if 'amigo' in response:
        ret.append("Knockback")
        engine.say("Knoback")
        engine.runAndWait()
    if 'tela' in response:
        ret.append("Lava source block")
        engine.say("Bloco de lava")
        engine.runAndWait()
    if 'poggers' in response:
        ret.append("Heal 1 heart")
        engine.say("Curando 1 coração")
        engine.runAndWait()
    if 'saúde' in response:
        ret.append("No effects for 20 seconds")
        engine.say("Sem efeitos por 20 segundos")
        engine.runAndWait()
    if 'caralho' in response:
        ret.append("Instant death")
        engine.say("Morte instantânea")
        engine.runAndWait()
    if 'coisa' in response:
        ret.append("Give iron nugget")
        engine.say("Pepita de ferro")
        engine.runAndWait()
    if 'foda' in response:
        ret.append("Strength effect")
        engine.say("Efeito de força")
        engine.runAndWait()
    if 'salve' in response:
        ret.append("Drop inventory")
        engine.say("Dropando inventário")
        engine.runAndWait()


    print(response, ret)

    ret.append(res)
    return ret


while 1:
    try:
        with mic as src:
            r.adjust_for_ambient_noise(src)
            audio = r.listen(src)
            resp = r.recognize_google(audio, language='pt-BR')
            cmd = get_response(resp)


            mc.stream(cmd)

    except speech_recognition.UnknownValueError:
        pass