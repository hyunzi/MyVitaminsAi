import React from 'react';
import { Tooltip } from 'react-tooltip';
import { FaRegQuestionCircle } from "react-icons/fa";

const QuestionTooltip = ({text}) => {
    return (
        <div className='inline'>
            <button className='icon-container'>
                <FaRegQuestionCircle className='ml-1 mb-0.5 inline text-sm inline' data-tooltip-content={text} data-tooltip-id='tooltip' />
            </button>

            <Tooltip
                id='tooltip'
                backgroundColor='gray'
                place="top"
                arrowColor='transparent'
            />
        </div>
    );
};

export default QuestionTooltip;