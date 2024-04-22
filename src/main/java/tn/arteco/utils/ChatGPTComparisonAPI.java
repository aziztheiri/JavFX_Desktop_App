package tn.arteco.utils;


import com.plexpt.chatgpt.ChatGPT;

public class ChatGPTComparisonAPI {
    public  String sendChatMessage(String message) {
        ChatGPT chatGPT = ChatGPT.builder()
                
                .build()
                .init();
        return chatGPT.chat(" i am in a website that's related to art ,  i want to make a comparison between two arts selected by the user : here are my two objects make a comparison that  is brief and precised and just give me the comparison in bullet point  and remove the introductive and conclusion phrases that you usually use ( en français ) " + message + " l'output doit etre de cette forme ( si ily'a une egalité d'attrobut il faut la mentionner ) : le premier oeuvre  est intitulé ... et le deuxième est intitulé ....  - le nom du premier artiste est ... et le nom du deuxième artiste est ....  - le premier oeuvre appartient a la catégorie .... et le deuxième appartient a la  catégorie ....  - si'l y'a un rapport entre la description et l'art site le  - le totalrate du premier oeuvre est ....  ( plus grand ou plus petit ) que le totalrate du deuxième oeuvre qui est ..... , -le premier oeuvre a été deposé en ..... et le deuxième en ..... ");
    }
}