import React from 'react';
import Header from './components/Header';
import './global.scss';
import VitaminsCarousel from "./components/VitaminsCarousel";
import AskVitamins from "./components/AskVitamins";

const App = () => {
    return (
        <div>
            <Header/>
            <VitaminsCarousel/>
            <AskVitamins/>
        </div>
    );
};

export default App;