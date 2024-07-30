import React from 'react';

const SkeletonAskVitamins = () => {
    return (
        <div className="flex space-x-4 pl-8 pr-8 pt-3">
            <div className="w-1/2 p-4 bg-white rounded-lg shadow-lg">
                <div className="animate-pulse space-y-4">
                    <div className="flex items-center space-x-2">
                        <div className="w-8 h-8 bg-gray-200 rounded-full"></div>
                        <div className="w-40 h-6 bg-gray-200 rounded"></div>
                    </div>
                    <div className="space-y-2">
                        <div className="w-full h-8 bg-gray-200 rounded"></div>
                        <div className="w-full h-8 bg-gray-200 rounded"></div>
                        <div className="w-full h-8 bg-gray-200 rounded"></div>
                        <div className="w-full h-8 bg-gray-200 rounded"></div>
                        <div className="w-full h-8 bg-gray-200 rounded"></div>
                        <div className="w-full h-8 bg-gray-200 rounded"></div>
                    </div>
                    <div className="flex justify-end">
                        <div className="w-24 h-8 bg-gray-200 rounded"></div>
                    </div>
                </div>
            </div>

            <div className="w-1/2 p-4 bg-white rounded-lg shadow-lg">
                <div className="animate-pulse space-y-4">
                    <div className="flex items-center space-x-2">
                        <div className="w-8 h-8 bg-gray-200 rounded-full"></div>
                        <div className="w-40 h-6 bg-gray-200 rounded"></div>
                    </div>
                    <div className="flex space-x-4">
                        <div className="w-1/2 h-60 bg-gray-200 rounded"></div>
                        <div className="w-1/2 h-60 bg-gray-200 rounded"></div>
                    </div>
                    <div className="flex justify-end">
                        <div className="w-32 h-8 bg-gray-200 rounded"></div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SkeletonAskVitamins;