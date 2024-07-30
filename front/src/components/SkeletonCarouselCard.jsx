import React from 'react';

const SkeletonCarouselCard = () => {
    return (
        <div className="w-1/3 p-4 mx-auto bg-white rounded-lg shadow-lg">
            <div className="animate-pulse flex space-x-4">
                <div className="rounded-full bg-gray-200 h-10 w-10"></div>
                <div className="flex-1 space-y-4 py-1">
                    <div className="h-4 bg-gray-200 rounded w-3/4"></div>
                    <div className="space-y-2">
                        <div className="h-4 bg-gray-200 rounded"></div>
                        <div className="h-4 bg-gray-200 rounded w-5/6"></div>
                        <div className="h-4 bg-gray-200 rounded w-5/6"></div>
                        <div className="h-4 bg-gray-200 rounded w-3/6"></div>
                        <div className="h-4 bg-gray-200 rounded w-3/6"></div>
                        <div className="h-4 bg-gray-200 rounded w-2/6"></div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SkeletonCarouselCard;