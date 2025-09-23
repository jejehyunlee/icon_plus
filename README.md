Result 
soal no 1 :

soal no 2 :

## API Response Example

**Endpoint:** `GET (http://localhost:8080/api/booking/summary)`

**Response:**
```json

{
    "period": "January 2024",
    "statistics": {
        "totalBookings": 21,
        "totalParticipants": 601,
        "averageUtilization": 70.0,
        "totalConsumptionCost": 27320000
    },
    "roomUtilizations": [
        {
            "roomName": "Ruang Ganesha",
            "bookingCount": 3,
            "utilizationRate": 30.0,
            "totalConsumption": 3240000
        },
        {
            "roomName": "Ruang Semeru",
            "bookingCount": 3,
            "utilizationRate": 30.0,
            "totalConsumption": 1760000
        },
        {
            "roomName": "Ruang Borobudur",
            "bookingCount": 3,
            "utilizationRate": 30.0,
            "totalConsumption": 5940000
        },
        {
            "roomName": "Ruang Mendhut",
            "bookingCount": 2,
            "utilizationRate": 20.0,
            "totalConsumption": 1650000
        },
        {
            "roomName": "Ruang Fatmawati",
            "bookingCount": 2,
            "utilizationRate": 20.0,
            "totalConsumption": 2350000
        },
        {
            "roomName": "Ruang Prambanan",
            "bookingCount": 2,
            "utilizationRate": 20.0,
            "totalConsumption": 4200000
        },
        {
            "roomName": "Ruang Sudirman",
            "bookingCount": 2,
            "utilizationRate": 20.0,
            "totalConsumption": 2070000
        },
        {
            "roomName": "Ruang Galunggung",
            "bookingCount": 1,
            "utilizationRate": 10.0,
            "totalConsumption": 1610000
        },
        {
            "roomName": "Ruang Arjuna",
            "bookingCount": 1,
            "utilizationRate": 10.0,
            "totalConsumption": 1750000
        },
        {
            "roomName": "Ruang Rinjani",
            "bookingCount": 1,
            "utilizationRate": 10.0,
            "totalConsumption": 1350000
        },
        {
            "roomName": "Ruang Bromo",
            "bookingCount": 1,
            "utilizationRate": 10.0,
            "totalConsumption": 1400000
        }
    ],
    "consumptionSummary": {
        "snackPagi": 0,
        "snacksiang": 9380000,
        "makanSiang": 11640000,
        "snackSore": 6300000,
        "otherConsumptions": 0
    },
    "officeActivity": {
        "mostActiveOffice": "UID JAYA",
        "officeBookingCount": 7,
        "mostUsedRoom": "Ruang Ganesha",
        "roomUsageCount": 3
    }
}
