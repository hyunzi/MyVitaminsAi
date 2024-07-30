import React from 'react';
import { FaCommentMedical } from "react-icons/fa";

const VitaminModal = ({vitaminList, onOpenModal}) => {

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
                                {vitaminList.map((vitamin, index) => {
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