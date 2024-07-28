import React, { useCallback, useEffect, useState } from 'react'
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Slider from "react-slick";
import { GoThumbsup } from "react-icons/go";
import { CiWarning } from "react-icons/ci";
import { FaExclamation } from "react-icons/fa";

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

    const dataList =   [
        {
            "name": "종합비타민",
            "effect": ["다양한 비타민과 미네랄을 공급하여 건강 유지에 도움"],
            "time": "아침 식사 후",
            "caution": ["과다 복용 시 오히려 부작용 발생 가능", "개인별 맞춤형 종합비타민 선택 권장"],
            "imgUrl" : "https://cloudinary.images-iherb.com/image/upload/f_auto,q_auto:eco/images/cgn/cgn00965/m/113.jpg"
        },
        {
            "name": "유산균",
            "effect": ["장 건강 개선", "면역력 증진"],
            "time": "식사와 상관없이 언제든지",
            "caution": ["냉장 보관 필수", "장 기능 저하 시 복용 전 전문가 상담"],
            "imgUrl" : "https://cloudinary.images-iherb.com/image/upload/f_auto,q_auto:eco/images/cgn/cgn00965/m/113.jpg"
        },
        {
            "name": "오메가-3",
            "effect": ["혈행 개선", "염증 감소"],
            "time": "식사와 함께",
            "caution": ["혈액 응고제 복용 시 주의", "어패류 알레르기 있을 경우 주의"],
            "imgUrl" : "https://cloudinary.images-iherb.com/image/upload/f_auto,q_auto:eco/images/cgn/cgn00965/m/113.jpg"
        },
        {
            "name": "아연",
            "effect": ["면역력 증진", "상처 치유 촉진"],
            "time": "식사와 함께",
            "caution": ["구리 흡수를 방해할 수 있음", "과다 복용 시 구토, 설사 등 부작용 발생 가능"],
            "imgUrl" : "https://cloudinary.images-iherb.com/image/upload/f_auto,q_auto:eco/images/cgn/cgn00965/m/113.jpg"
        },
        {
            "name": "마그네슘",
            "effect": ["근육 이완", "수면 개선"],
            "time": "저녁 식사 후",
            "caution": ["설사 유발 가능", "신장 질환 환자는 주의"],
            "imgUrl" : "https://cloudinary.images-iherb.com/image/upload/f_auto,q_auto:eco/images/cgn/cgn00965/m/113.jpg"
        },
        {
            "name": "비타민 D",
            "effect": ["칼슘 흡수 촉진", "면역력 강화"],
            "time": "식사와 상관없이 언제든지",
            "caution": ["과다 복용 시 고칼슘혈증을 유발할 수 있음"],
            "imgUrl" : "https://cloudinary.images-iherb.com/image/upload/f_auto,q_auto:eco/images/cgn/cgn00965/m/113.jpg"
        }
    ];

    return (
        <div>
            <div className='ms-4 mt-3 w-100 pt-2'>
                <FaExclamation className='text-3xl mb-3 text-red-600 inline me-2 drop-shadow-md'/>
                <span className='leading-3 text-3xl drop-shadow-md'>오늘의 추천</span>
                <div className='ms-4 text-desc'>많은 사람들이 찾는 영양제 혹은 당신이 물어본 데이터를 기반으로 영양제를 추천합니다.</div>
            </div>
            <div className='px-4 slider-div'>
                <div className="slider-container">
                    <Slider {...settings}>
                        {dataList.map((data, index) => {
                            return <div key={index} className='slider-inner-div'>
                                <div className='m-1'>
                                    <div className='px-4 pt-2 ps-7'>
                                    <span>
                                        <img src={data.imgUrl} alt='' className='vitamin-image div-center float-right mt-7'/>
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
        </div>
    );
}

export default VitaminsCarousel;
