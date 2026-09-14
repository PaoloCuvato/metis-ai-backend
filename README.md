# Metis-Ai-Backend
 
# Cos'è Metis AI e perché è nato
Metis AI è un progetto open source creato da me che aiuta nell'assisterti con la creazione di immagini o generazione di asset di ogni tipo e nello sviluppo tramite il chatbot.

# Metis AI - Backend

Questo repository ospita esclusivamente la componente **Backend** dell'architettura di Metis AI, occupandosi della logica di business, delle API REST e dell'orchestrazione dei servizi di intelligenza artificiale.

## Tecnologie Utilizzate
* **Linguaggio:** Java 21
* **Framework:** Spring Boot, Spring Data, Spring AI
* **Database:** PostgreSQL
* **Interfaccia Frontend:** Angular (gestito in un repository separato)

## Requisiti e Servizi Locali
* **Ollama:** Configurato per l'esecuzione in locale di modelli linguistici testuali.
* **ComfyUI:** Esecuzione in locale di pipeline di diffusione per la generazione e manipolazione di asset grafici.
* **PostgreSQL:** Database relazionale per la persistenza di dati, prompt e metadati.
* **Maven:** Strumento di build e gestione delle dipendenze.
