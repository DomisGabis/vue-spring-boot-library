import { createRouter, createWebHistory } from 'vue-router';

import MainLayout from '../layouts/MainLayout.vue';

import HomeView from '../views/HomeView.vue';
import BooksView from '../views/books/BooksView.vue';
import AuthorsView from '../views/authors/AuthorsView.vue';
import ReadersView from '../views/readers/ReadersView.vue';
import RentalsView from '../views/rentals/RentalsView.vue';
import BookDetailsView from '../views/books/BookDetailsView.vue';
import BookEditView from '../views/books/BookEditView.vue';
import BookAddView from '../views/books/BookAddView.vue';
import AuthorDetailsView from '../views/authors/AuthorDetailsView.vue';
import AuthorEditView from '../views/authors/AuthorEditView.vue';
import AuthorAddView from '../views/authors/AuthorAddView.vue';
import RentalAddView from '../views/rentals/RentalAddView.vue';
import ReaderDetailsView from '../views/readers/ReaderDetailsView.vue';
import ReaderEditView from '../views/readers/ReaderEditView.vue';
import ReaderAddView from '../views/readers/ReaderAddView.vue';

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
            },
            {
                path: 'authors/:id',
                name: 'AuthorDetails',
                component: AuthorDetailsView
            },
            {
                path: 'authors/:id/edit',
                name: 'AuthorEdit',
                component: AuthorEditView
            },
            {
                path: 'authors/add',
                name: 'AuthorAdd',
                component: AuthorAddView
            },
            {
                path: 'rentals/add',
                name: 'RentalAdd',
                component: RentalAddView
            },
            {
                path: 'readers/:id',
                name: 'ReaderDetails',
                component: ReaderDetailsView
            },
            {
                path: 'readers/:id/edit',
                name: 'ReaderEdit',
                component: ReaderEditView
            },
            {
                path: 'readers/add',
                name: 'ReaderAdd',
                component: ReaderAddView
            }
        ]
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;