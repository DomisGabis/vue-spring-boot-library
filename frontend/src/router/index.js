import { createRouter, createWebHistory } from 'vue-router';

import MainLayout from '../layouts/MainLayout.vue';

import HomeView from '../views/HomeView.vue';
import BooksView from '../views/BooksView.vue';
import AuthorsView from '../views/AuthorsView.vue';
import ReadersView from '../views/ReadersView.vue';
import RentalsView from '../views/RentalsView.vue';

const routes = [
    {
        path: '/',
        component: MainLayout,
        children: [
            {
                path: '',
                name: 'Home',
                component: HomeView
            },
            {
                path: 'books',
                name: 'Books',
                component: BooksView
            },
            {
                path: 'authors',
                name: 'Authors',
                component: AuthorsView
            },
            {
                path: 'readers',
                name: 'Readers',
                component: ReadersView
            },
            {
                path: 'rentals',
                name: 'Rentals',
                component: RentalsView
            }
        ]
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;