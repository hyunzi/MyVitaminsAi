import React from 'react';
import '../global.scss';


const Header = () => {
    return (
        <div>
            <div className='w-full bg-yellow-300 h-2'/>
            <div className='p-3 border-solid border-b-2 shadow-md'>
                <span className='inline'>
                    <img className="inline align-middle h-10 w-auto" src="/images/icon.png" alt=""/>
                    <span className='p-3 text-3xl align-middle'>
                        My Vitamins AI
                    </span>
                </span>
            </div>
        </div>
    );
};

export default Header;