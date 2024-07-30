import React, { useCallback, useEffect, useState } from 'react'
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Slider from "react-slick";
import { GoThumbsup } from "react-icons/go";
import { CiWarning } from "react-icons/ci";
import { FaExclamation } from "react-icons/fa";
import axios from "../axios";
import useStore from "../store.js";
import SkeletonCarousel from "./SkeletonCarousel";

const VitaminsCarousel = () => {
    const settings = {
        dots: true,
        infinite: true,
        slidesToShow: 3,
        slidesToScroll: 1,
        autoplay: true,
        speed: 5000,
        autoplaySpeed: 8000,
        cssEase: "linear"
    };

    const loading = useStore((state) => state.loading);
    const setLoading = useStore((state) => state.setLoading);
    const defaultVitaminList = useStore((state) => state.defaultVitaminList);
    const [dataList, setDataList] = useState([]);

    useEffect(() => {
       // setTimeout(() => {
            // 데이터 요청 - 비동기 함수가 useEffect 내부에서 즉시 실행되도록 래핑
            (async () => {
                try {
                    setLoading(true);
                    const response = await axios.post('/api/recommend', { sessionKey: localStorage.getItem('user') });
                    setDataList(response.data.supplements);

                } catch (error) {
                    console.error('Error fetching data:', error);
                    setDataList(defaultVitaminList);

                } finally {
                    setLoading(false);
                }
            })();
      //  }, 10000);
    }, []);

    return (
        <div>
            <div className='ms-4 mt-3 w-100 pt-2'>
                <FaExclamation className='text-3xl mb-3 text-red-600 inline me-2 drop-shadow-md'/>
                <span className='leading-3 text-3xl drop-shadow-md'>오늘의 추천</span>
                <div className='ms-4 text-desc'>많은 사람들이 찾는 영양제 혹은 당신이 물어본 데이터를 기반으로 영양제를 추천합니다.</div>
            </div>
            {loading ?
                <SkeletonCarousel/>
                :
                <>
                    <div className='px-4 slider-div'>
                        <div className="slider-container">
                            <Slider {...settings}>
                                {dataList.map((data, index) => {
                                    return <div key={index} className='slider-inner-div'>
                                        <div className='m-1'>
                                            <div className='px-4 pt-2 ps-7'>
                                    <span>
                                        <img
                                            src={data.imgUrl}
                                            alt=''
                                            className='vitamin-image div-center float-right mt-7'
                                            onError={(e) => (e.target.src = '/images/defaultVitamin.png')}
                                        />
                                    </span>
                                                <span className='text-left text-3xl font-bold'>{data.name}</span>
                                            </div>
                                            <div className='px-4 pt-0.5 ps-7'>
                                    <span className='btn-info text-xs time-tag'>
                                        {data.time}
                                    </span>
                                            </div>
                                        </div>
                                        <div className='m-2 pt-2 px-4 ps-7'>
                                            <div className='effect-title'>
                                                <GoThumbsup className='text-green-600 inline mb-1'/>
                                                <span className='ms-1 inline'>좋아요</span>
                                            </div>
                                            <>
                                                {data.effect.map((eff,index) => {
                                                    return <div className='ms-5 font-light text-sm mb-2'>
                                                        {eff}
                                                    </div>
                                                })}
                                            </>
                                        </div>
                                        <div className='m-2 pt-2 px-4 ps-7'>
                                            <div className='effect-title'>
                                                <CiWarning className='text-red-600 inline mb-1 font-bold'/>
                                                <span className='ms-1 inline'>조심해요</span>
                                            </div>
                                            <>
                                                {data.caution.map((cau,index) => {
                                                    return <div className='ms-5 font-light text-sm mb-2'>
                                                        {cau}
                                                    </div>
                                                })}
                                            </>
                                        </div>
                                    </div>
                                })}
                            </Slider>
                        </div>
                    </div>
                </>
            }
        </div>
    );
}

export default VitaminsCarousel;
