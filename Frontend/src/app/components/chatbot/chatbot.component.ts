import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface ChatMessage {
    text: string;
    isUser: boolean;
    timestamp: Date;
}

@Component({
    selector: 'app-chatbot',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './chatbot.component.html',
    styleUrl: './chatbot.component.css'
})
export class ChatbotComponent implements OnInit {
    messages: ChatMessage[] = [];
    userInput = '';
    loading = false;

    botResponses: { [key: string]: string } = {
        'hello': 'Hello! 👋 Welcome to Smart Travel Planner. How can I help you plan your trip today?',
        'help': 'I can help you with:\n• Finding destinations\n• Planning trips\n• Hotel recommendations\n• Travel tips and safety information\n• Food recommendations\n\nWhat would you like to know?',
        'destination': 'We have amazing destinations like Goa, Kerala, Manali, Rajasthan, and Ladakh! Which one interests you?',
        'hotel': 'I can help you find the best hotels within your budget. Which destination are you interested in?',
        'budget': 'Tell me your budget and preferences, and I\'ll suggest the best options for you!',
        'safety': 'Your safety is important! We provide information about:\n• Nearby hospitals and emergency services\n• Safety alerts for destinations\n• Local emergency contacts\n• Travel tips\n\nWhich destination are you asking about?',
        'food': 'I love talking about food! 🍜 We have guides for:\n• Traditional cuisines\n• Local specialties\n• Vegetarian & vegan options\n• Budget-friendly eats\n\nWhich destination\'s cuisine interests you?',
        'cost': 'Trip costs depend on:\n• Destination\n• Duration\n• Budget level (Budget/Standard/Luxury)\n• Number of travelers\n• Activities & dining choices',
        'thank': 'You\'re welcome! Happy to help! 😊 Is there anything else?',
        'default': 'That\'s an interesting question! I can help you with destination planning, hotel searches, safety information, and food recommendations. Feel free to ask! 😊'
    };

    constructor() { }

    ngOnInit() {
        this.sendBotMessage("Welcome to Smart Travel Planner! 🌍 I'm your AI assistant. Type your question or say 'help' for suggestions!");
    }

    sendMessage() {
        if (this.userInput.trim() === '') return;

        const userMessage: ChatMessage = {
            text: this.userInput,
            isUser: true,
            timestamp: new Date()
        };

        this.messages.push(userMessage);
        const userQuery = this.userInput.toLowerCase();
        this.userInput = '';

        this.loading = true;
        setTimeout(() => {
            this.getBotResponse(userQuery);
            this.loading = false;
        }, 500);
    }

    private getBotResponse(query: string) {
        let response = this.botResponses['default'];

        for (const key of Object.keys(this.botResponses)) {
            if (key !== 'default' && query.includes(key)) {
                response = this.botResponses[key];
                break;
            }
        }

        this.sendBotMessage(response);
    }

    private sendBotMessage(text: string) {
        const botMessage: ChatMessage = {
            text: text,
            isUser: false,
            timestamp: new Date()
        };
        this.messages.push(botMessage);
    }

    clearChat() {
        this.messages = [];
        this.sendBotMessage("Chat cleared! How can I help you? 😊");
    }
}
