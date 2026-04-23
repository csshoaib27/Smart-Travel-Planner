import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { SafetyAlert, TravelTip } from '../models/safety-alert.model';

@Injectable({
    providedIn: 'root'
})
export class SafetyService {
    private safetyAlerts: SafetyAlert[] = [
        {
            id: 'safety-001',
            destinationId: 'dest-001',
            city: 'Goa',
            alertType: 'hospital',
            name: 'Goa Medical College',
            description: 'Government medical college and hospital',
            address: 'Bambolim, Goa',
            phone: '+91-832-2450300',
            latitude: 15.478,
            longitude: 73.828,
            distance: 5.2,
            operatingHours: '24/7',
            services: ['Emergency', 'Trauma', 'Surgery', 'ICU']
        },
        {
            id: 'safety-002',
            destinationId: 'dest-001',
            city: 'Goa',
            alertType: 'police',
            name: 'Panaji Police Station',
            description: 'Local police station for emergencies',
            address: 'Panaji, Goa',
            phone: '+91-832-2425267',
            latitude: 15.497,
            longitude: 73.827,
            distance: 2.1,
            operatingHours: '24/7',
            services: ['Emergency response', 'FIR registration', 'Information']
        },
        {
            id: 'safety-003',
            destinationId: 'dest-002',
            city: 'Manali',
            alertType: 'hospital',
            name: 'Manali Civil Hospital',
            description: 'Primary health center for tourists',
            address: 'Old Manali, Himachal Pradesh',
            phone: '+91-1902-252575',
            latitude: 32.237,
            longitude: 77.189,
            distance: 3.5,
            operatingHours: '24/7',
            services: ['Emergency', 'Altitude sickness treatment', 'General care']
        }
    ];

    private travelTips: TravelTip[] = [
        {
            id: 'tip-001',
            destinationId: 'dest-001',
            category: 'weather',
            title: 'Monsoon weather',
            description: 'June to September is monsoon season with heavy rainfall',
            essentials: ['Raincoat', 'Umbrella', 'Waterproof bag'],
            season: 'June-September'
        },
        {
            id: 'tip-002',
            destinationId: 'dest-002',
            category: 'weather',
            title: 'Altitude sickness',
            description: 'Manali is at high altitude, drink plenty of water and rest on first day',
            essentials: ['Water bottle', 'Medication', 'Oxygen'],
            season: 'Year-round'
        },
        {
            id: 'tip-003',
            destinationId: 'dest-005',
            category: 'weather',
            title: 'Snow gear essentials',
            description: 'Ladakh is very cold, pack thermal wear and heavy jackets',
            essentials: ['Thermal wear', 'Snow jacket', 'Gloves', 'Snow boots'],
            season: 'June-September'
        },
        {
            id: 'tip-004',
            destinationId: 'dest-005',
            category: 'entry',
            title: 'Permits required',
            description: 'Some areas need permits from SDM office, arrange in advance',
            essentials: ['ID proof', 'Passport copy'],
            season: 'Always'
        }
    ];

    private emergencyContacts = {
        police: '100',
        ambulance: '102',
        fireService: '101',
        touristPolice: '1363'
    };

    constructor() { }

    getSafetyAlertsByDestination(destinationId: string): Observable<SafetyAlert[]> {
        return of(this.safetyAlerts.filter(a => a.destinationId === destinationId));
    }

    getSafetyAlertByType(destinationId: string, type: string): Observable<SafetyAlert[]> {
        return of(this.safetyAlerts.filter(a => a.destinationId === destinationId && a.alertType === type));
    }

    getTravelTipsByDestination(destinationId: string): Observable<TravelTip[]> {
        return of(this.travelTips.filter(t => t.destinationId === destinationId));
    }

    getTravelTipsByCategory(destinationId: string, category: string): Observable<TravelTip[]> {
        return of(this.travelTips.filter(t => t.destinationId === destinationId && t.category === category));
    }

    getNearbyHospitals(destinationId: string): Observable<SafetyAlert[]> {
        return of(this.safetyAlerts.filter(a => a.destinationId === destinationId && a.alertType === 'hospital'));
    }

    getNearbyPoliceStations(destinationId: string): Observable<SafetyAlert[]> {
        return of(this.safetyAlerts.filter(a => a.destinationId === destinationId && a.alertType === 'police'));
    }

    getEmergencyContacts(): Observable<any> {
        return of(this.emergencyContacts);
    }

    addSafetyAlert(alert: SafetyAlert): Observable<SafetyAlert> {
        alert.id = 'safety-' + Math.random().toString(36).substr(2, 9);
        this.safetyAlerts.push(alert);
        return of(alert);
    }

    addTravelTip(tip: TravelTip): Observable<TravelTip> {
        tip.id = 'tip-' + Math.random().toString(36).substr(2, 9);
        this.travelTips.push(tip);
        return of(tip);
    }
}
