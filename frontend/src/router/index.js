import { createRouter, createWebHistory } from 'vue-router';

import MainLayout from '../layouts/MainLayout.vue';

import HomeView from '../views/HomeView.vue';
import BooksView from '../views/BooksView.vue';
import AuthorsView from '../views/AuthorsView.vue';
import ReadersView from '../views/ReadersView.vue';
import RentalsView from '../views/RentalsView.vue';
import BookDetailsView from '../views/BookDetailsView.vue';
import BookEditView from '../views/BookEditView.vue';
import BookAddView from '../views/BookAddView.vue';

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
            },
            {
                path: 'books/:id',
                name: 'BookDetails',
                component: BookDetailsView
            },
            {
                path: 'books/:id/edit',
                name: 'BookEdit',
                component: BookEditView
            },
            {
                path: 'books/add',
                name: 'BookAdd',
                component: BookAddView
            }
        ]
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;