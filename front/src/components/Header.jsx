import React from 'react';
import '../global.scss';
import { FiRefreshCcw } from "react-icons/fi";


const Header = () => {
    return (
        <div>
            <div className='w-full bg-yellow-300 h-2'/>
            <div className='p-4 border-solid border-b-2 shadow-md'>
                <span className='inline'>
                    <img className="inline align-middle h-14 w-auto" src="/images/icon.png" alt=""/>
                    <span className='p-4 text-3xl align-middle'>
                        My Vitamins AI
                    </span>
                </span>
            </div>
        </div>
    );
};

export default Header;