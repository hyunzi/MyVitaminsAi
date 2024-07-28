import React from 'react';
import { FaCommentMedical } from "react-icons/fa";

const VitaminModal = ({vitaminList, onOpenModal}) => {
    const askVitaminList = [
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
        <div className="h-screen w-full fixed left-0 top-0 flex justify-center items-center bg-black bg-opacity-70 text-center">
            <div className="bg-white rounded w-11/12 lg:w-2/3">
                <div className="border-b px-4 py-2 flex">
                    <FaCommentMedical className='inline mr-2 mt-1'/>
                    <span className="font-extrabold">영양제 상세</span>
                </div>
                <div className="border-b px-4 py-2 flex justify-between items-center">
                    <div className="border rounded-lg shadow overflow-hidden w-full">
                        <table className="min-w-full divide-y divide-gray-200 vitamins-table">
                            <thead className="bg-gray-50">
                            <tr>
                                <th scope="col">영양제명</th>
                                <th scope="col">복용권장시간</th>
                                <th scope="col">효과</th>
                                <th scope="col">유의점</th>
                            </tr>
                            </thead>
                            <tbody className="divide-y divide-gray-200">
                            <>
                                {askVitaminList.map((vitamin, index) => {
                                    return <tr key={index}>
                                        <td>{vitamin.name}</td>
                                        <td>{vitamin.time}</td>
                                        <td>{vitamin.effect.join('\n')}</td>
                                        <td>{vitamin.caution.join('\n')}</td>
                                    </tr>
                                })}
                            </>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div className="flex justify-end items-center w-100 border-t p-2 text-gray-500">
                    <button className="bg-gray-600 hover:bg-gray-700 px-3 py-0.5 rounded text-white text-xs" onClick={() => onOpenModal(false) }>
                        닫기
                    </button>
                </div>
            </div>
        </div>
    );
};

export default VitaminModal;