import React, { useCallback, useEffect, useState } from 'react'
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Slider from "react-slick";
import { GrStatusGood } from "react-icons/gr";

const VitaminsCarousel = () => {
    const settings = {
        dots: true,
        infinite: true,
        slidesToShow: 3,
        slidesToScroll: 1,
       // autoplay: true,
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
        <div className='mt-8 px-8 slider-div'>
            <div className="slider-container">
                <Slider {...settings}>
                    {dataList.map((data, index) => {
                        return <div key={index} className='slider-inner-div'>
                            <div className='m-2'>
                                <div className='px-4 pt-4 ps-7'>
                                    <span>
                                        <img src={data.imgUrl} alt='' className='vitamin-image div-center float-right'/>
                                    </span>
                                    <span className='text-left text-5xl font-bold'>{data.name}</span>
                                </div>
                                <div className='px-4 pt-2 ps-7'>
                                    <span className='btn-primary text-xs'>
                                        {data.time}
                                    </span>
                                </div>
                            </div>
                        </div>
                    })}
                </Slider>
            </div>
        </div>
    );
}

export default VitaminsCarousel
