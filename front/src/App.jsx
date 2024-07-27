import React from 'react';
import Header from './components/Header';
import './global.scss';
import VitaminsCarousel from "./components/VitaminsCarousel";

const App = () => {
    return (
        <div>
            <Header/>
            <VitaminsCarousel/>
        </div>
    );
};

export default App;