import React from 'react';
import SkeletonCarouselCard from "./SkeletonCarouselCard";

const SkeletonCarousel = () => {
    return (
        <div className="pl-8 pr-8 pt-3">
            <div className="flex space-x-4 pt-5 pb-5">
                <SkeletonCarouselCard/>
                <SkeletonCarouselCard/>
                <SkeletonCarouselCard/>
            </div>
        </div>
    );
};

export default SkeletonCarousel;