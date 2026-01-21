import http from 'k6/http';

export let options = {
    stages: [
        { duration: '30s', target: 20 },
        { duration: '30s', target: 50 },
        { duration: '30s', target: 100 },
    ]
};

export default function () {
    http.post(
        'http://localhost:18080/orders',
        JSON.stringify({ productId: 1, quantity: 1 }),
        { headers: { 'Content-Type': 'application/json' } }
    );
}
